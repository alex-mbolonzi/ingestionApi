import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ValidationNotes } from 'app/shared/model/validation-notes.model';
import { ValidationNotesService } from './validation-notes.service';
import { ValidationNotesComponent } from './validation-notes.component';
import { ValidationNotesDetailComponent } from './validation-notes-detail.component';
import { ValidationNotesUpdateComponent } from './validation-notes-update.component';
import { ValidationNotesDeletePopupComponent } from './validation-notes-delete-dialog.component';
import { IValidationNotes } from 'app/shared/model/validation-notes.model';

@Injectable({ providedIn: 'root' })
export class ValidationNotesResolve implements Resolve<IValidationNotes> {
  constructor(private service: ValidationNotesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IValidationNotes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ValidationNotes>) => response.ok),
        map((validationNotes: HttpResponse<ValidationNotes>) => validationNotes.body),
      );
    }
    return of(new ValidationNotes());
  }
}

export const validationNotesRoute: Routes = [
  {
    path: '',
    component: ValidationNotesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.validationNotes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ValidationNotesDetailComponent,
    resolve: {
      validationNotes: ValidationNotesResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.validationNotes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ValidationNotesUpdateComponent,
    resolve: {
      validationNotes: ValidationNotesResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.validationNotes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ValidationNotesUpdateComponent,
    resolve: {
      validationNotes: ValidationNotesResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.validationNotes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const validationNotesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ValidationNotesDeletePopupComponent,
    resolve: {
      validationNotes: ValidationNotesResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.validationNotes.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
