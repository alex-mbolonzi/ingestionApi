import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DatasetDataCategory } from 'app/shared/model/dataset-data-category.model';
import { DatasetDataCategoryService } from './dataset-data-category.service';
import { DatasetDataCategoryComponent } from './dataset-data-category.component';
import { DatasetDataCategoryDetailComponent } from './dataset-data-category-detail.component';
import { DatasetDataCategoryUpdateComponent } from './dataset-data-category-update.component';
import { DatasetDataCategoryDeletePopupComponent } from './dataset-data-category-delete-dialog.component';
import { IDatasetDataCategory } from 'app/shared/model/dataset-data-category.model';

@Injectable({ providedIn: 'root' })
export class DatasetDataCategoryResolve implements Resolve<IDatasetDataCategory> {
  constructor(private service: DatasetDataCategoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDatasetDataCategory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DatasetDataCategory>) => response.ok),
        map((datasetDataCategory: HttpResponse<DatasetDataCategory>) => datasetDataCategory.body),
      );
    }
    return of(new DatasetDataCategory());
  }
}

export const datasetDataCategoryRoute: Routes = [
  {
    path: '',
    component: DatasetDataCategoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDataCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetDataCategoryDetailComponent,
    resolve: {
      datasetDataCategory: DatasetDataCategoryResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDataCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetDataCategoryUpdateComponent,
    resolve: {
      datasetDataCategory: DatasetDataCategoryResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDataCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetDataCategoryUpdateComponent,
    resolve: {
      datasetDataCategory: DatasetDataCategoryResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDataCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const datasetDataCategoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DatasetDataCategoryDeletePopupComponent,
    resolve: {
      datasetDataCategory: DatasetDataCategoryResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDataCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
