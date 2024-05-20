import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DatasetIndication } from 'app/shared/model/dataset-indication.model';
import { DatasetIndicationService } from './dataset-indication.service';
import { DatasetIndicationComponent } from './dataset-indication.component';
import { DatasetIndicationDetailComponent } from './dataset-indication-detail.component';
import { DatasetIndicationUpdateComponent } from './dataset-indication-update.component';
import { DatasetIndicationDeletePopupComponent } from './dataset-indication-delete-dialog.component';
import { IDatasetIndication } from 'app/shared/model/dataset-indication.model';

@Injectable({ providedIn: 'root' })
export class DatasetIndicationResolve implements Resolve<IDatasetIndication> {
  constructor(private service: DatasetIndicationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDatasetIndication> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DatasetIndication>) => response.ok),
        map((datasetIndication: HttpResponse<DatasetIndication>) => datasetIndication.body),
      );
    }
    return of(new DatasetIndication());
  }
}

export const datasetIndicationRoute: Routes = [
  {
    path: '',
    component: DatasetIndicationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetIndication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetIndicationDetailComponent,
    resolve: {
      datasetIndication: DatasetIndicationResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetIndication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetIndicationUpdateComponent,
    resolve: {
      datasetIndication: DatasetIndicationResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetIndication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetIndicationUpdateComponent,
    resolve: {
      datasetIndication: DatasetIndicationResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetIndication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const datasetIndicationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DatasetIndicationDeletePopupComponent,
    resolve: {
      datasetIndication: DatasetIndicationResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetIndication.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
