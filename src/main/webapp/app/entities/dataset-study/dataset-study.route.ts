import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DatasetStudy } from 'app/shared/model/dataset-study.model';
import { DatasetStudyService } from './dataset-study.service';
import { DatasetStudyComponent } from './dataset-study.component';
import { DatasetStudyDetailComponent } from './dataset-study-detail.component';
import { DatasetStudyUpdateComponent } from './dataset-study-update.component';
import { DatasetStudyDeletePopupComponent } from './dataset-study-delete-dialog.component';
import { IDatasetStudy } from 'app/shared/model/dataset-study.model';

@Injectable({ providedIn: 'root' })
export class DatasetStudyResolve implements Resolve<IDatasetStudy> {
  constructor(private service: DatasetStudyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDatasetStudy> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DatasetStudy>) => response.ok),
        map((datasetStudy: HttpResponse<DatasetStudy>) => datasetStudy.body),
      );
    }
    return of(new DatasetStudy());
  }
}

export const datasetStudyRoute: Routes = [
  {
    path: '',
    component: DatasetStudyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetStudy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetStudyDetailComponent,
    resolve: {
      datasetStudy: DatasetStudyResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetStudy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetStudyUpdateComponent,
    resolve: {
      datasetStudy: DatasetStudyResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetStudy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetStudyUpdateComponent,
    resolve: {
      datasetStudy: DatasetStudyResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetStudy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const datasetStudyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DatasetStudyDeletePopupComponent,
    resolve: {
      datasetStudy: DatasetStudyResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetStudy.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
